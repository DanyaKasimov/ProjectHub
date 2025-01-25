import { createRouter, createWebHistory } from 'vue-router'
import RegisterCompanyPage from "@/views/RegisterCompanyPage.vue";
import LoginEmployeePage from "@/views/LoginEmployeePage.vue";
import EmployeeProfilePage from "@/views/EmployeeProfilePage.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'employee_signin',
      component: LoginEmployeePage,
    },
    {
      path: '/company/register',
      name: 'signup',
      component: RegisterCompanyPage,
    },
    {
      path: '/employee/login',
      name: 'signin',
      component: LoginEmployeePage,
    },
    {
      path: '/employee/profile',
      name: 'profile',
      component: EmployeeProfilePage,
    },
    // {
    //   path: '/about',
    //   name: 'about',
    //   // route level code-splitting
    //   // this generates a separate chunk (About.[hash].js) for this route
    //   // which is lazy-loaded when the route is visited.
    //   component: () => import('../views/AboutView.vue'),
    // },
  ],
})

export default router
