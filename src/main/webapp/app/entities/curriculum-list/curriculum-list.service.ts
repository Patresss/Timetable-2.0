import {Injectable} from '@angular/core';
import {Http, Response} from '@angular/http';
import {CurriculumService} from '../curriculum';
import {CurriculumList} from './curriculum-list.model';
import {DivisionOwnerEntityService} from '../division-owner-entity.service';
import {Observable} from 'rxjs/Rx';
import {SERVER_API_URL} from '../../app.constants';

@Injectable()
export class CurriculumListService extends DivisionOwnerEntityService<CurriculumList> {

    protected generateUrl = SERVER_API_URL + 'api/generator?curriculumListId=';

    constructor(http: Http, private curriculumService: CurriculumService) {
        super(http, 'curriculum-listes')
    }

    generate(curriculumId: number): Observable<any> {
        console.log("in gej")
        console.log(this.generateUrl + curriculumId.toString())
        return this.http.post(this.generateUrl + curriculumId.toString(), null);
    }



    convertFromServer(jsonResponse: any) {
        if (jsonResponse.curriculumTimes != null) {
            for (let i = 0; i < jsonResponse.curriculumTimes.length; i++) {
                this.curriculumService.convertFromServer(jsonResponse.curriculumTimes[i]);
            }
        }
    }

    convertToServer(curriculumList: CurriculumList): CurriculumList {
        const copy: CurriculumList = Object.assign({}, curriculumList);
        copy.curriculums = Object.assign([], curriculumList.curriculums);
        if (copy.curriculums != null) {
            for (let i = 0; i < copy.curriculums.length; i++) {
                copy.curriculums[i] = this.curriculumService.convertToServer(copy.curriculums[i]);
            }
        }
        return copy;
    }

}
