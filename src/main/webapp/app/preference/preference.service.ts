import {Injectable} from '@angular/core';
import {BaseRequestOptions, Http, Response, URLSearchParams} from '@angular/http';
import {Observable} from 'rxjs/Rx';

import {ResponseWrapper} from '../shared';
import {SERVER_API_URL} from '../app.constants';
import {PreferenceDependency} from './preferecne-dependency.model';
import {PreferenceHierarchy} from './preferecne-hierarchy.model';

@Injectable()
export class PreferenceService {

    protected resourceUrl: string;

    constructor(protected http: Http) {
        this.resourceUrl = SERVER_API_URL + 'api/' + 'preferences';
    }

    getPreferenceByPreferenceDependency(preferenceDependency: PreferenceDependency): Observable<ResponseWrapper> {
        return this.http.get(this.resourceUrl, this.createRequestOptionWithPreferenceDependency(preferenceDependency))
            .map((res: Response) => this.convertResponses(res));
    }

    convertResponses(res: Response): ResponseWrapper {
        const jsonResponse = res.json();

        const teacherMap = new Map<number, PreferenceHierarchy>();
        if (jsonResponse.preferredTeacherMap) {
            Object.keys(jsonResponse.preferredTeacherMap).forEach((key) => {
                teacherMap.set(Number(key), jsonResponse.preferredTeacherMap[key]);
            });
        }
        jsonResponse.preferredTeacherMap = teacherMap;

        const subjectMap = new Map<number, PreferenceHierarchy>();
        if (jsonResponse.preferredSubjectMap) {
            Object.keys(jsonResponse.preferredSubjectMap).forEach((key) => {
                subjectMap.set(Number(key), jsonResponse.preferredSubjectMap[key]);
            });
        }
        jsonResponse.preferredSubjectMap = subjectMap;

        const placeMap = new Map<number, PreferenceHierarchy>();
        if (jsonResponse.preferredPlaceMap) {
            Object.keys(jsonResponse.preferredPlaceMap).forEach((key) => {
                placeMap.set(Number(key), jsonResponse.preferredPlaceMap[key]);
            });
        }
        jsonResponse.preferredPlaceMap = placeMap;

        const divisionMap = new Map<number, PreferenceHierarchy>();
        if (jsonResponse.preferredDivisionMap) {
            Object.keys(jsonResponse.preferredDivisionMap).forEach((key) => {
                divisionMap.set(Number(key), jsonResponse.preferredDivisionMap[key]);
            });
        }
        jsonResponse.preferredDivisionMap = divisionMap;

        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    createRequestOptionWithPreferenceDependency = (preferenceDependency: PreferenceDependency): BaseRequestOptions => {
        const options: BaseRequestOptions = new BaseRequestOptions();
        if (preferenceDependency) {
            const params: URLSearchParams = new URLSearchParams();
            if (preferenceDependency.subjectId) {
                params.set('subjectId', preferenceDependency.subjectId.toString());
            }
            if (preferenceDependency.teacherId) {
                params.set('teacherId', preferenceDependency.teacherId.toString());
            }
            if (preferenceDependency.divisionId) {
                params.set('divisionId', preferenceDependency.divisionId.toString());
            }
            if (preferenceDependency.placeId) {
                params.set('placeId', preferenceDependency.placeId.toString());
            }
            if (preferenceDependency.periodId) {
                params.set('periodId', preferenceDependency.periodId.toString());
            }
            if (preferenceDependency.lessonId) {
                params.set('lessonId', preferenceDependency.lessonId.toString());
            }
            if (preferenceDependency.divisionOwnerId) {
                params.set('divisionOwnerId', preferenceDependency.divisionOwnerId.toString());
            }
            if (preferenceDependency.date) {
                params.set('date', preferenceDependency.date.toString());
            }
            if (preferenceDependency.inMonday) {
                params.set('inMonday', preferenceDependency.inMonday.toString());
            }
            if (preferenceDependency.inTuesday) {
                params.set('inTuesday', preferenceDependency.inTuesday.toString());
            }
            if (preferenceDependency.inWednesday) {
                params.set('inWednesday', preferenceDependency.inWednesday.toString());
            }
            if (preferenceDependency.inThursday) {
                params.set('inThursday', preferenceDependency.inThursday.toString());
            }
            if (preferenceDependency.inFriday) {
                params.set('inFriday', preferenceDependency.inFriday.toString());
            }
            if (preferenceDependency.inSaturday) {
                params.set('inSaturday', preferenceDependency.inSaturday.toString());
            }
            if (preferenceDependency.inSunday) {
                params.set('inSunday', preferenceDependency.inSunday.toString());
            }
            if (preferenceDependency.everyWeek) {
                params.set('everyWeek', preferenceDependency.everyWeek.toString());
            }
            if (preferenceDependency.startWithWeek) {
                params.set('startWithWeek', preferenceDependency.startWithWeek.toString());
            }
            if (preferenceDependency.startTimeString) {
                params.set('startTimeString', preferenceDependency.startTimeString.toString());
            }
            if (preferenceDependency.endTimeString) {
                params.set('endTimeString', preferenceDependency.endTimeString.toString());
            }
            options.params = params;
        }
        return options;
    };
}
