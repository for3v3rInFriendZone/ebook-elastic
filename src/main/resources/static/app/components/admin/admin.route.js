(function() {
	"use strict";

	angular
		.module('ebook-admin')
		.config(config);


	config.$inject = ['$urlRouterProvider', '$stateProvider'];
	function config($urlRouterProvider, $stateProvider){

		$stateProvider
		.state("main.usersEdit", {
			url: '/user/:id',
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
		})
		.state("main.newUser", {
			url: '/users/new',
			views:{
				'main@': {
					resolve: {
						categories: getCategories,
						listOfUsers: getUsers		
					},
					templateUrl: "app/components/admin/adminNewUser.html",
					controller: "AdminUserController",
					controllerAs: "auc"
				}
			}
		});
		
		getUserToEdit.$inject = ['User', '$stateParams'];
		function getUserToEdit(User, $stateParams) {
			return User.findOne($stateParams.id);
		}
		
		getCategories.$inject = ['Category'];
		function getCategories(Category) {
			return Category.findAll();
		}
		
		getUsers.$inject = ['User'];
		function getUsers(User) {
			return User.findAll();
		}
	}
})();
