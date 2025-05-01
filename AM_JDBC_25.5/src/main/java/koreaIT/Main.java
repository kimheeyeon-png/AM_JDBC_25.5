package koreaIT;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        System.out.println("== 프로그램 시작 ==");

        Scanner sc = new Scanner(System.in);
        int lastId = 0;
        List<Article> articleList = new ArrayList<>();


        while(true){
            System.out.print("명령어) ");
            String cmd = sc.nextLine().trim();

            if(cmd.equals("exit")){
                System.out.println("== 프로그램 종료 ==");
                break;
            }
            else if (cmd.equals("article write")){
                System.out.print("제목 : ");
                String title = sc.nextLine().trim();
                System.out.print("내용 : ");
                String body = sc.nextLine().trim();
                lastId++;

                Article addArticle = new Article(lastId, title, body);
                articleList.add(addArticle);

                System.out.printf("%d번 게시글이 등록되었습니다.", lastId);
            }
            else if (cmd.equals("article list")){
                System.out.println("번호     /     제목 ");
                for (int i = articleList.size() - 1; i >= 0; i--){
                    System.out.printf("%d     /     %s\n", articleList.get(i).getId(), articleList.get(i).getTitle());
                }
            }
        }
    }
}