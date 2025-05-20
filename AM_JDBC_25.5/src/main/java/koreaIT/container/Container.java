package koreaIT.container;

import koreaIT.session.Session;

public class Container {

    public static Session session;

    public static void init() {
        session = new Session();
    }
}
