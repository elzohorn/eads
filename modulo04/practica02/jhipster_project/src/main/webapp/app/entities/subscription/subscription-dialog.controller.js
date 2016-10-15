(function() {
    'use strict';

    angular
        .module('modulo04Practica02JhipsterApp')
        .controller('SubscriptionDialogController', SubscriptionDialogController);

    SubscriptionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Subscription', 'Person', 'Channel'];

    function SubscriptionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Subscription, Person, Channel) {
        var vm = this;

        vm.subscription = entity;
        vm.clear = clear;
        vm.save = save;
        vm.subscribers = Person.query({filter: 'subscribedpodcast-is-null'});
        $q.all([vm.subscription.$promise, vm.subscribers.$promise]).then(function() {
            if (!vm.subscription.subscriber || !vm.subscription.subscriber.id) {
                return $q.reject();
            }
            return Person.get({id : vm.subscription.subscriber.id}).$promise;
        }).then(function(subscriber) {
            vm.subscribers.push(subscriber);
        });
        vm.channels = Channel.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.subscription.id !== null) {
                Subscription.update(vm.subscription, onSaveSuccess, onSaveError);
            } else {
                Subscription.save(vm.subscription, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('modulo04Practica02JhipsterApp:subscriptionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
