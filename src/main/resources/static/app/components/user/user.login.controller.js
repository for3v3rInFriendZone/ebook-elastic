(function() {
	'use strict';

	angular
		.module('ebook-user')
		.controller('LoginController', LoginController);

	LoginController.$inject = ['User', '$state', 'localStorageService', 'users'];
	function LoginController(User, $state, localStorageService, users) {
		
		var ulc = this;
		
		ulc.listOfUsers = users;
		ulc.submitForm = submitForm;
		ulc.focus = focus;
		ulc.userPage = userPage;
		ulc.loginFailed = false;
		
		function submitForm() {

			for(var i=0; i<ulc.listOfUsers.length; i++) {
				if(ulc.username === ulc.listOfUsers[i].username) {
					if(ulc.password === ulc.listOfUsers[i].password) {
						ulc.user = ulc.listOfUsers[i];
						localStorageService.set('user', ulc.user);
						$state.go('main.user', {username: ulc.username});
						return;
					}
				}	
			}
			
			ulc.loginFailed = true;
		}
		
		function focus() {
			ulc.loginFailed = false;
		}
		
		
		function userPage() {
			$state.go('userPage', {username: ulc.username});
		}
	}
	
})();
