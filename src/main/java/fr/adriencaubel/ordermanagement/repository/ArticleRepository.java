
package fr.adriencaubel.ordermanagement.repository;

import fr.adriencaubel.ordermanagement.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
