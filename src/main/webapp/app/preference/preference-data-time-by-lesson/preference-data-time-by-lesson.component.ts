import {Component, Input} from '@angular/core';
import {SelectType} from '../../util/select-type.model';
import {AngularMultiSelectComponent} from '../../components/angular2-multiselect-dropdown/multiselect.component';
import {Preference} from '../preferecne.model';

@Component({
    selector: 'jhi-preference-data-time-by-lesson',
    templateUrl: './preference-data-time-by-lesson.component.html',
})
export class PreferenceDataTimeByLessonComponent {

    preferenceDataTimeSelectOption = Preference.preferenceSelectTypes;

    @Input()
    preferenceDataTimeByLessonList = [];


    // TODO to samo w multiselectt=
    getOptionHierarchyStyle(item: any) {
        let color = 'transparent';
        if (item.value) {
            if (item.value > 0) {
                const colorAlpha = item.value / 10.0;
                color = 'rgba(40, 167, 69, ' + colorAlpha + ')';
                return {'border-left': '10px solid ' + color};
            } else if (item.value < 0) {
                const colorAlpha = item.value / 10.0;
                color = 'rgba(220, 53, 69, ' + colorAlpha + ')';
                return {'border-left': '10px solid ' + color};
            }
        }
        return {'border-left': '10px solid ' + color};
    }
}
