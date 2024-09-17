package com.dariom.ichirowalks.view.route;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

import static com.vaadin.flow.component.icon.VaadinIcon.CALENDAR_O;
import static com.vaadin.flow.component.icon.VaadinIcon.LIST_UL;
import static com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment.CENTER;
import static com.vaadin.flow.theme.lumo.LumoUtility.*;

@Route("")
public class Home extends AppLayout {

    public Home() {
        // title
        var title = new H1("Ichiro Walks");
        title.getStyle()
                .set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "var(--lumo-space-m) var(--lumo-space-l)");
        title.addClassNames("headline");

        // navbar
        var navBar = buildNavBar();
        addToNavbar(title);
        addToNavbar(true, navBar);
    }

    private HorizontalLayout buildNavBar() {
        var navbar = new HorizontalLayout();
        navbar.addClassNames(Width.FULL, JustifyContent.EVENLY, AlignSelf.STRETCH, Padding.SMALL);

        navbar.add(
                createLink(TodayWalks.class, CALENDAR_O, "Today"),
                createLink(AllWalks.class, LIST_UL, "All")
        );

        return navbar;
    }

    private RouterLink createLink(Class<? extends Component> route, VaadinIcon icon, String viewName) {
        // label
        var label = new Span(viewName);
        label.getStyle().set("font-size", "var(--lumo-font-size-s)");

        // container for icon and label
        var container = new VerticalLayout(icon.create(), label);
        container.setSpacing(false);
        container.setPadding(false);
        container.setAlignItems(CENTER);

        // router link
        var link = new RouterLink();
        link.setRoute(route);
        link.getElement().setAttribute("aria-label", viewName);
        link.addClassNames(Display.FLEX, AlignItems.CENTER, Padding.Horizontal.LARGE, TextColor.SECONDARY);
        link.add(container);

        return link;
    }
}
