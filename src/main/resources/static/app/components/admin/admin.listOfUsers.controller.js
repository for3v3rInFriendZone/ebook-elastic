(function() {
	'use strict';

	angular
		.module('ebook-admin')
		.controller('AdminListUsersController', AdminListUsersController);

	AdminListUsersController.$inject = ['localStorageService', '$state', 'User', 'listOfUsers'];
	function AdminListUsersController(localStorageService, $state, User, listOfUsers) {
		
		var aluc = this;
		aluc.newUser = newUser;
		aluc.editListOfUsers = editListOfUsers;
		aluc.users = listOfUsers;
		
		/**
		 * Dont show a loged user in list of users.
		 */
		for(var i=0; i<aluc.users.length; i++) {
			if(aluc.users[i].id === localStorageService.get('user').id) {
				aluc.users.splice(i, 1);
				break;
			}
		}
		
		function editListOfUsers(id) {	
			
			$state.go('main.usersEdit', {id:id});
		}
		
		function newUser() {
			$state.go('main.newUser');
		}
		

	}
})();
