package infrastructure.persistence.mapper;

public interface MapperJPA  <E,J> {

    J toJPAEntity (E entity);

    E toEntity (J jpaEntity);
}
