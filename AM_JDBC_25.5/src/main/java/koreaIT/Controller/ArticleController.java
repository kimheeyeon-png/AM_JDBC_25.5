package koreaIT.controller;

import koreaIT.service.ArticleService;
import util.DBUtil;
import util.SecSql;

import java.sql.Connection;
import java.util.Map;
import java.util.Scanner;

public class ArticleController {
    private ArticleService articleService = null;
    private Connection conn;
    private Scanner sc;

    public ArticleController(Connection conn, Scanner sc) {
        this.conn = conn;
        this.sc = sc;
        this.articleService = new ArticleService(conn);
    }

    public void doDelete(String cmd) {
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

        articleService.doDelete(id);

        System.out.printf("%d번 게시글이 삭제되었습니다.\n", id);
    }
}