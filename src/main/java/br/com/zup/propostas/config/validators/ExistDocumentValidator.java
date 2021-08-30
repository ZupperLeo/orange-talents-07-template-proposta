package br.com.zup.propostas.config.validators;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class ExistDocumentValidator implements ConstraintValidator<CPFCNPJValidator, Object> {

    private String domainAttribute;
    private Class<?> klass;
    @PersistenceContext
    private EntityManager manager;

    @Override
    public void initialize(CPFCNPJValidator constraintAnnotation) {
        domainAttribute = constraintAnnotation.fieldName();
        klass = constraintAnnotation.domainClass();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if(value == null) {
            return false;
        }

        Query query = manager.createQuery("SELECT 1 FROM " + klass.getName() +
                " WHERE " + domainAttribute + "=:value");
        query.setParameter("value", value);

        List<?> list = query.getResultList();

        return !list.isEmpty();
    }
}
