import IRoute from "./route";
import HomePage from "../../home/home";
import InventoryPage from "../../inventory/inventory";
import LibraryPage from "../../library/library";
import LibrariesPage from "../../library/libraries";
import BookPage from "../../book/book";
import SignInPage from "../../auth/signin";
import SignUpPage from "../../auth/signup";
import ProfilePage from "../../profile/profile";
import CampaignPage from "../../campaign/campaign";

const routes: IRoute[] = [
    {
        path: '/',
        name: 'Home Page',
        component: HomePage,
        exact: true
    },
    {
        path: '/profile',
        name: 'Profile page',
        component: ProfilePage,
        exact: true
    },
    {
        path: '/library',
        name: "Libraries Page",
        component: LibrariesPage,
        exact: true
    },
    {
        path: '/library/:libraryId',
        name: 'Library Page',
        component: LibraryPage,
        exact: true
    },
    {
        path: '/library/:libraryId/inventory',
        name: 'Inventory Page',
        component: InventoryPage,
        exact: true
    },
    {
        path: '/library/:libraryId/inventory/:inventoryId',
        name: 'Book Page',
        component: BookPage,
        exact: true
    }, 
    {
        path: '/library/:libraryId/campaign/:campaignId',
        name: "Campaign Page",
        component: CampaignPage,
        exact: true
    },
    {
        path: '/sign-in',
        name: 'Sign In Page',
        component: SignInPage,
        exact: true
    },
    {
        path: '/sign-up',
        name: 'Sign Up Page',
        component: SignUpPage,
        exact: true
    }
];

export default routes;