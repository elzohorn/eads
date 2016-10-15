'use strict';

describe('Controller Tests', function() {

    describe('Subscription Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockSubscription, MockPerson, MockChannel;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockSubscription = jasmine.createSpy('MockSubscription');
            MockPerson = jasmine.createSpy('MockPerson');
            MockChannel = jasmine.createSpy('MockChannel');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Subscription': MockSubscription,
                'Person': MockPerson,
                'Channel': MockChannel
            };
            createController = function() {
                $injector.get('$controller')("SubscriptionDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'modulo04Practica02JhipsterApp:subscriptionUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
