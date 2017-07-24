/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { TimetableTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { TeacherDetailComponent } from '../../../../../../main/webapp/app/entities/teacher/teacher-detail.component';
import { TeacherService } from '../../../../../../main/webapp/app/entities/teacher/teacher.service';
import { Teacher } from '../../../../../../main/webapp/app/entities/teacher/teacher.model';

describe('Component Tests', () => {

    describe('Teacher Management Detail Component', () => {
        let comp: TeacherDetailComponent;
        let fixture: ComponentFixture<TeacherDetailComponent>;
        let service: TeacherService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TimetableTestModule],
                declarations: [TeacherDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    TeacherService,
                    JhiEventManager
                ]
            }).overrideTemplate(TeacherDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TeacherDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TeacherService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Teacher(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.teacher).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
