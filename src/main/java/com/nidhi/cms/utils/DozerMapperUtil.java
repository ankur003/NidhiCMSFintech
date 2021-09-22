package com.nidhi.cms.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.dozer.Mapper;
import org.dozer.MappingException;

/**
 * 
 *
 * @author Ankur Bansala
 */

public final class DozerMapperUtil {
	/**
	 * No Args constructor.
	 */
	private DozerMapperUtil() {
		throw new UnsupportedOperationException(
				this.getClass().getCanonicalName() + ", being a utility class, is not allowed to be instantiated.");
	}

	@SuppressWarnings("unchecked")
	public static <S, D> List<Object> mapCollection(final Mapper beanMapper, final Collection<S> srcCollection,
			final Class<D> targetBeanClass) {
		return mapCollection(beanMapper, srcCollection, ArrayList.class, targetBeanClass, targetBeanClass);
	}

	@SuppressWarnings("unchecked")
	public static <S, D> Set<D> mapCollectionSet(final Mapper beanMapper, final Collection<S> srcCollection,
			final Class<D> targetBeanClass) {
		return mapCollection(beanMapper, srcCollection, LinkedHashSet.class, targetBeanClass, targetBeanClass);
	}

	@SuppressWarnings("unchecked")
	public static <S, CC, D extends CC> List<CC> mapCollection(final Mapper beanMapper,
			final Collection<S> srcCollection, final Class<D> targetBeanClass, final Class<CC> castToBeanClass) {
		return mapCollection(beanMapper, srcCollection, ArrayList.class, targetBeanClass, castToBeanClass);
	}

	public static <S, CC, D extends CC, DC extends Collection<CC>> DC mapCollection(final Mapper beanMapper,
			final Collection<S> srcCollection, final Class<DC> destCollectionClass, final Class<D> targetBeanClass,
			final Class<CC> castToBeanClass) {
		if (srcCollection == null || srcCollection.isEmpty()) {
			return null;
		}
		DC destCollection = null;
		try {
			destCollection = destCollectionClass.newInstance();
		} catch (final InstantiationException ie) {
			throw new MappingException(ie);
		} catch (final IllegalAccessException iae) {
			throw new MappingException(iae);
		}
		for (final S source : srcCollection) {
			destCollection.add(beanMapper.map(source, targetBeanClass));
		}
		return destCollection;
	}

	@SuppressWarnings("unchecked")
	public static <S, D> Set<D> mapCollectionSet(final Mapper beanMapper, final Collection<S> srcCollection,
			final Collection<D> destCollection, final String fieldNameToCompare, final Class<D> targetBeanClass) {
		return mapCollection(beanMapper, srcCollection, LinkedHashSet.class, destCollection, fieldNameToCompare,
				targetBeanClass);
	}

	private static <S, D, DC extends Collection<D>> DC mapCollection(final Mapper beanMapper,
			final Collection<S> srcCollection, final Class<DC> destCollectionClass, final Collection<D> destCollection,
			final String fieldNameToCompare, final Class<D> targetBeanClass) {
		if (srcCollection == null || srcCollection.isEmpty() || destCollection == null || destCollection.isEmpty()) {
			return null;
		}

		DC newDestCollection = null;
		try {
			newDestCollection = destCollectionClass.newInstance();
		} catch (final InstantiationException instantiationException) {
			throw new MappingException(instantiationException);
		} catch (final IllegalAccessException illegalAccessException) {
			throw new MappingException(illegalAccessException);
		}

		for (final S source : srcCollection) {
			try {
				final Field sourceField = source.getClass().getDeclaredField(fieldNameToCompare);
				sourceField.setAccessible(true);
				if (sourceField.get(source) == null) {
					newDestCollection.add(beanMapper.map(source, targetBeanClass));
					continue;
				}
				for (final D destination : destCollection) {
					final Field destField = destination.getClass().getDeclaredField(fieldNameToCompare);
					destField.setAccessible(true);
					if (sourceField.get(source) != null && destField.get(destination) != null
							&& sourceField.get(source).equals(destField.get(destination))) {
						final D newDestination = beanMapper.map(destination, targetBeanClass);
						beanMapper.map(source, newDestination);
						newDestCollection.add(newDestination);
					}
				}
			} catch (final IllegalArgumentException illegalArgumentException) {
				throw new MappingException(illegalArgumentException);
			} catch (final IllegalAccessException illegalAccessException) {
				throw new MappingException(illegalAccessException);
			} catch (final NoSuchFieldException noSuchFieldException) {
				throw new MappingException(noSuchFieldException);
			} catch (final SecurityException securityException) {
				throw new MappingException(securityException);
			} catch (final MappingException mappingException) {
				throw new MappingException(mappingException);
			}
		}

		return newDestCollection;
	}

}
