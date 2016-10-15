(function() {
    'use strict';

    angular
        .module('modulo04Practica02JhipsterApp')
        .controller('PodcastDialogController', PodcastDialogController);

    PodcastDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Podcast', 'Person', 'Channel'];

    function PodcastDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Podcast, Person, Channel) {
        var vm = this;

        vm.podcast = entity;
        vm.clear = clear;
        vm.save = save;
        vm.people = Person.query();
        vm.channels = Channel.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.podcast.id !== null) {
                Podcast.update(vm.podcast, onSaveSuccess, onSaveError);
            } else {
                Podcast.save(vm.podcast, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('modulo04Practica02JhipsterApp:podcastUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
