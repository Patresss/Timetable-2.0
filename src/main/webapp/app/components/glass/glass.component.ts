import {Component, ElementRef, HostBinding, Input, Renderer} from '@angular/core';

@Component({
  selector: 'glass',
  templateUrl: './glass.component.html',
  styleUrls: ['./glass.component.scss'],

})
export class GlassComponent {
  title = 'glass';

    @Input()
    contentClass: string;
}
