package koreaIT.controller;

import koreaIT.dto.Article;
import koreaIT.container.Container;
import koreaIT.service.ArticleService;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ArticleController {
    private ArticleService articleService;
    private Scanner sc;

    public ArticleController() {
        this.articleService = Container.articleService;
        this.sc = Container.sc;
    }

    public void doDelete(String cmd) {

        if (!Container.session.isLogined()) {
            System.out.println("로그인 후 이용하세요.");
            return;
        }

        int id = -1;

        //parsing
        try {
            id = Integer.parseInt(cmd.split(" ")[2]);
        } catch (Exception e) {
            System.out.println("정수 입력하세요");
            return;
        }
        //parsing 까지

        // 글 유무체크
        Map<String, Object> articleMap = articleService.getArticleById(id);
        if (articleMap.isEmpty()) {
            System.out.printf("%d번 글은 없습니다.\n", id);
            return;
        }
        // 글 유무체크 끝

// article의 id로 가져온 게시글 데이터 중 memberId vs. Container.session.loginedMemberId
        Article article = new Article(articleMap);

        if (!(article.getId() == Container.session.loginedMemberId)) {
            System.out.println("삭제권한이 없습니다.");
            return;
        }
        articleService.doDelete(id);

        System.out.printf("%d번 게시글이 삭제되었습니다.\n", id);
    }

    public void showDetail(String cmd) {

        int id = 0;

        //parsing
        try {
            id = Integer.parseInt(cmd.split(" ")[2]);
        } catch (Exception e) {
            System.out.println("정수 입력하세요");
        }
        //parsing 까지

        // 글 유무체크
        Map<String, Object> articleMap = articleService.getArticleById(id);
        if (articleMap.isEmpty()) {
            System.out.printf("%d번 글은 없습니다.\n", id);
            return;
        }
        // 글 유무체크 끝

        System.out.println("== 상세보기 ==");

        Article article = new Article(articleMap);

        System.out.println("번호 : " + article.getId());
        System.out.println("등록날짜 : " + article.getRegDate());
        System.out.println("수정날짜 : " + article.getUpdateDate());
//        System.out.println("작성자번호 : " + article.getMemberId());
        System.out.println("작성자 : " + article.getName());
        System.out.println("제목 : " + article.getTitle());
        System.out.println("내용 : " + article.getBody());

    }

    public void doModify(String cmd) {

        if (!Container.session.isLogined()) {
            System.out.println("로그인 후 이용하세요.");
            return;
        }

        int id = 0;

        //parsing
        try {
            id = Integer.parseInt(cmd.split(" ")[2]);
        } catch (Exception e) {
            System.out.println("정수 입력하세요");
        }
        //parsing 까지

        // 글 유무체크
        Map<String, Object> articleMap = articleService.getArticleById(id);
        if (articleMap.isEmpty()) {
            System.out.printf("%d번 글은 없습니다.\n", id);
            return;
        }
        // 글 유무체크 끝

        // article의 id로 가져온 게시글 데이터 중 memberId vs. Container.session.loginedMemberId

        Article article = new Article(articleMap);

        if (!(article.getId() == Container.session.loginedMemberId)) {
            System.out.println("수정권한이 없습니다.");
            return;
        }

        System.out.println("== 글 수정 ==");
        System.out.print("새 제목 : ");
        String newTitle = sc.nextLine().trim();
        System.out.print("새 내용 : ");
        String newBody = sc.nextLine().trim();

        articleService.doModify(id, newTitle, newBody);

        System.out.printf("%d번 게시글이 수정되었습니다.\n", id);
    }

    public void doWrite() {

        if (!Container.session.isLogined()) {
            System.out.println("로그인 후 이용하세요");
            return;
        }

        System.out.print("제목 : ");
        String title = sc.nextLine().trim();
        System.out.print("내용 : ");
        String body = sc.nextLine().trim();

        int id = articleService.doWrite(title, body);

        System.out.printf("%d번 게시글이 등록되었습니다.\n", id);
    }

    public void showList(String cmd) {

        int page = 0;

        // parsing
        try {
            page = Integer.parseInt(cmd.split(" ")[2]);
        } catch (Exception e) {
            System.out.println("정수 입력하세요");
        }
        // parsing 까지

        List<Article> articleList = Container.articleService.getArticles(page);


        if (articleList.size() == 0) {
            System.out.println("게시글이 없습니다.");
            return;
        }

        System.out.println("번호    /     제목      /     작성자");
        for (Article article : articleList) {
            System.out.printf("%d      /   %s      /   %s\n", article.getId(), article.getTitle(), article.getName());
        }
    }
}