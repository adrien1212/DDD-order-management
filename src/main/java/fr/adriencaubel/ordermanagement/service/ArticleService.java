
package fr.adriencaubel.ordermanagement.service;

import fr.adriencaubel.ordermanagement.domain.Article;
import fr.adriencaubel.ordermanagement.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    public Article getArticle(Long id) {
        return articleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Article not found"));
    }
}
