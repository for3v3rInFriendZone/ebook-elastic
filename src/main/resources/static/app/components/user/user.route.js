(function() {
	"use strict";

	angular
		.module('ebook-user')
		.config(config);


	config.$inject = ['$urlRouterProvider', '$stateProvider'];
	function config($urlRouterProvider, $stateProvider){

		$stateProvider
		.state("login", {
			url: '/login',
			views:{
				'main@': {
					resolve: {
						users: getUsers
					},
					templateUrl: "app/components/user/login.html",
					controller: "LoginController",
					controllerAs: "ulc"
				}
			}
		})
		.state("main.user", {
			url: '/:username',
			views:{
				'main@': {
					templateUrl: "app/components/user/main.html",
					controller: "UserController",
					controllerAs: "ucr"
				}
			}
		})
		.state("main.userPage", {
			url: '/profile/:username',
			views:{
				'main@': {
					templateUrl: "app/components/user/mainUserPage.html",
					controller: "UserController",
					controllerAs: "ucr"
				}
			}
		})
		.state("main.userEdit", {
			url: '/profile/:username/:id',
			views:{
				'main@': {
					resolve: {
						selectedUser: getUserToEdit,
						categories: getCategories
					},
					templateUrl: "app/components/user/user.editUser.html",
					controller: "UserEditController",
					controllerAs: "uec"
				}
			}
		});
		
		
		getUsers.$inject = ['User'];
		function getUsers(User) {
			return User.findAll();
		}
		
		getUserToEdit.$inject = ['User', '$stateParams'];
		function getUserToEdit(User, $stateParams) {
			return User.findOne($stateParams.id);
		}
		
		getCategories.$inject = ['Category'];
		function getCategories(Category) {
			return Category.findAll();
		}
	}
})();
