(function() {
	'use strict';

	angular
		.module('ebook-core')
		.controller('NavbarController', NavbarController);

	NavbarController.$inject = ['$state', 'localStorageService'];
	function NavbarController($state, localStorageService) { 
		var nbc = this;
		
		nbc.userPage = userPage;
		nbc.goToAdminUsers = goToAdminUsers;
		nbc.currentState = $state.current.name;
		nbc.signOut = signOut;
		nbc.isActive = isActive;
		
		nbc.user = localStorageService.get('user');
		
		function userPage() {
			$state.go('main.userPage', {username: nbc.user.username});
		}
		
		function goToAdminUsers() {
			$state.go('main.adminUsers');
		}
		
		function signOut() {
			localStorageService.clearAll();
			nbc.user = false;
			$state.go('main.home');
		}
		
		function isActive(state) {
			return $state.current.name.indexOf(state) != -1;
		};
	}
	
})();