package com.sboard.Service;

import com.sboard.dto.ArticleDTO;
import com.sboard.entity.Article;
import com.sboard.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ModelMapper modelMapper;

    public int insertArticle(ArticleDTO articleDTO) {

        // 첨부 파일 객체(MultipartFile) 가져오기
        List<MultipartFile> files = articleDTO.getFiles();
        log.info("files size : " + files.size());

        for (MultipartFile file : files) {
            log.info("file name : " + file.getOriginalFilename());
        }

        // ModelMapper 를 이용해서 DTO 를 Entity 로 변환
        Article article = modelMapper.map(articleDTO, Article.class);
        log.info(article);

        // 저장(save 메서드는 entity 를 저장한 뒤 다시 select 하여 savedEntity 를 리턴 함
        Article savedArticle = articleRepository.save(article);

        // 저장된 글 번호 리턴
        return savedArticle.getNo();

    }

    public ArticleDTO selectArticle(int no) {
        Optional<Article> opt = articleRepository.findById(no);
        if (opt.isPresent()) {
            Article article = opt.get();
        }
        return null;
    }

    public List<ArticleDTO> selectAllArticles() {
        List<Article> articles = articleRepository.findAll();
        return null;
    }

    public void updateArticle(ArticleDTO articleDTO) {

    }

    public void deleteArticle(int no) {
        articleRepository.deleteById(no);
    }

}
