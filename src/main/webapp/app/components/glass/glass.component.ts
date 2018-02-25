import {Component, Input} from '@angular/core';

@Component({
  selector: 'glass',
  templateUrl: './glass.component.html',
  styleUrls: ['./glass.component.scss', '../../layouts/navbar/navbar.scss'],

})
export class GlassComponent {
  title = 'glass';

    @Input()
    contentClass: string = 'default-glass-content';
}
