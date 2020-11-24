package com.github.eljaiek.machinery.configuration.core;

import java.util.Map;
import java.util.function.Supplier;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class ImmutablePropertyFactory implements PropertyFactory {

  private final PropertyRepository propertyRepository;

  @Override
  public Property create(@NonNull Map.Entry<String, String> entry) {
    return create(entry.getKey(), entry.getValue());
  }

  @Override
  public Property create(String key, String value) {
    return create(key, () -> value);
  }

  private Property create(String key, Supplier<String> value) {

    if (key == null || key.isBlank()) {
      throw new IllegalArgumentException("key cannot be null or blank.");
    }

    return new ImmutableProperty(
        key, value.get(), propertyRepository::put, propertyRepository::remove);
  }

  @Override
  public Property create(String key) {
    return create(key, () -> propertyRepository.getValue(key));
  }
}
