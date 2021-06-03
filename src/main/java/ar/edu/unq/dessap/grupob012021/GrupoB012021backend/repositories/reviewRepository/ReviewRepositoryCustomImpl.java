package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.repositories.reviewRepository;

import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.review.Review;
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.review.ReviewCriteriaDTO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class ReviewRepositoryCustomImpl implements ReviewRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Review> findByCriteria (ReviewCriteriaDTO reviewCriteria, int pageNumber){
        return this.findByCriteria(reviewCriteria, pageNumber, true);
    }

    @Override
    public List<Review> findByCriteria (ReviewCriteriaDTO reviewCriteria){
        return this.findByCriteria(reviewCriteria, 0, false);
    }

    public List<Review> findByCriteria(ReviewCriteriaDTO reviewCriteria, int pageNumber, boolean pageable) {
        int pageSize = 10;
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Review> cr = cb.createQuery(Review.class);
        Root<Review> root = cr.from(Review.class);
        List <Predicate> predicates = new ArrayList<>();
        if (reviewCriteria.getPlatform() != null){
            predicates.add(cb.equal(root.get("platform"), reviewCriteria.getPlatform()));
        }
        if (reviewCriteria.getType() != null){
            predicates.add(cb.equal(root.get("type"), reviewCriteria.getType()));
        }
        if (reviewCriteria.getCountry() != null){
            predicates.add(cb.equal(root.get("country"), reviewCriteria.getCountry()));
        }
        if (reviewCriteria.getLanguage() != null){
            predicates.add(cb.equal(root.get("language"), reviewCriteria.getLanguage()));
        }
        if (reviewCriteria.getRating() > 0){
            predicates.add(cb.ge(root.get("rating"), reviewCriteria.getRating()));
        }
        if (reviewCriteria.getLikes() > 0){
            predicates.add(cb.ge(root.get("likes"), reviewCriteria.getLikes()));
        }
        if (reviewCriteria.isSpoilerAlert()){
            predicates.add(cb.isTrue(root.get("spoilerAlert")));
        }
        else {
            predicates.add(cb.isFalse(root.get("spoilerAlert")));
        }
        String orderBy = reviewCriteria.isSearchByRating() ? "rating" : "date";
        if(reviewCriteria.isAscending()){
            cr.orderBy(cb.asc(root.get(orderBy)));
        }
        else{
            cr.orderBy(cb.desc(root.get(orderBy)));
        }

        cr.select(root).where(predicates.toArray(Predicate[]::new));

        Query query = em.createQuery(cr);
        if (pageable) {
            query.setFirstResult((pageNumber - 1) * pageSize);
            query.setMaxResults(pageSize);
        }
        return query.getResultList();
    }
}
