import {Component, Input} from '@angular/core';

@Component({
    selector: 'jhi-glass',
    templateUrl: './glass.component.html',
    styleUrls: ['./glass.component.scss', '../../layouts/navbar/navbar.scss'],

})
export class GlassComponent {
    title = 'jhi-glass';

    @Input()
    contentClass = 'default-glass-content';
}
