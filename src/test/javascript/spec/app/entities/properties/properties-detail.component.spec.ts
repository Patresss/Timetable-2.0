/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { TimetableTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PropertyDetailComponent } from '../../../../../../main/webapp/app/entities/property/property-detail.component';
import { PropertyService } from '../../../../../../main/webapp/app/entities/property/property.service';
import { Property } from '../../../../../../main/webapp/app/entities/property/property.model';

describe('Component Tests', () => {

    describe('Property Management Detail Component', () => {
        let comp: PropertyDetailComponent;
        let fixture: ComponentFixture<PropertyDetailComponent>;
        let service: PropertyService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TimetableTestModule],
                declarations: [PropertyDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PropertyService,
                    JhiEventManager
                ]
            }).overrideTemplate(PropertyDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PropertyDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PropertyService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Property(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.property).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
