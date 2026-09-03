package vn.iotstar.filter;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;

/**
 * Bộ lọc SiteMesh 3 quản lý giao diện Layout cho toàn ứng dụng.
 */
public class MySiteMeshFilter extends ConfigurableSiteMeshFilter {

    @Override
    protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
        // Đặt tiền tố decorator là "/" để map đúng file /decorators/*.jsp
        builder.setDecoratorPrefix("/")
               .addExcludedPath("/decorators/*")
               .addExcludedPath("/WEB-INF/decorators/*")
               .addExcludedPath("/image*")
               .addExcludedPath("/login*")
               .addExcludedPath("/logout*")
               .addDecoratorPath("/admin/*", "decorators/admin.jsp")
               .addDecoratorPath("/profile*", "decorators/web.jsp")
               .addDecoratorPath("/user/*", "decorators/web.jsp");
    }
}
