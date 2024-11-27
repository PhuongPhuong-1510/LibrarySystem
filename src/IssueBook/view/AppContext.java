package IssueBook.view;

import HomePage.view.HomePageView;

/**
 * Lớp AppContext sử dụng mẫu Singleton để đảm bảo chỉ có một thể hiện của lớp này tồn tại.
 * Lớp này quản lý việc truy cập đến đối tượng HomePageView, giúp dễ dàng chia sẻ đối tượng này trong toàn bộ ứng dụng.
 */
public class AppContext {
    // Biến thể hiện duy nhất của lớp AppContext (Singleton)
    private static AppContext instance;
    // Đối tượng HomePageView sẽ được quản lý và cung cấp qua AppContext
    private HomePageView homePageView;

    /**
     * Constructor riêng tư để ngăn việc tạo đối tượng AppContext từ ngoài lớp.
     * Chỉ có thể tạo đối tượng AppContext qua phương thức getInstance().
     */
    private AppContext() {}

    /**
     * Phương thức lấy đối tượng duy nhất của lớp AppContext.
     * Nếu đối tượng chưa tồn tại, sẽ tạo mới.
     *
     * @return Thể hiện duy nhất của lớp AppContext
     */
    public static AppContext getInstance() {
        if (instance == null) {
            instance = new AppContext();
        }
        return instance;
    }

    /**
     * Phương thức lấy đối tượng HomePageView hiện tại.
     *
     * @return Đối tượng HomePageView được quản lý bởi AppContext
     */
    public HomePageView getHomePageView() {
        return homePageView;
    }

    /**
     * Phương thức để đặt đối tượng HomePageView.
     *
     * @param homePageView Đối tượng HomePageView cần được lưu trữ
     */
    public void setHomePageView(HomePageView homePageView) {
        this.homePageView = homePageView;
    }
}
