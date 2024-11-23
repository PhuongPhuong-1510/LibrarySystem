package IssueBook.view;

import HomePage.view.HomePageView;

public class AppContext {
    private static AppContext instance;
    private HomePageView homePageView;

    private AppContext() {}

    public static AppContext getInstance() {
        if (instance == null) {
            instance = new AppContext();
        }
        return instance;
    }

    public HomePageView getHomePageView() {
        return homePageView;
    }

    public void setHomePageView(HomePageView homePageView) {
        this.homePageView = homePageView;
    }
}
