import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-secundary-button',
  standalone: true,
  imports: [],
  templateUrl: './secundary-button.component.html',
  styleUrl: './secundary-button.component.scss'
})
export class SecundaryButtonComponent {
  @Input() label?: string = '';
  @Output() onClick = new EventEmitter<Event | void>();

  handleClick(event: Event): void {
    this.onClick.emit(event);
  }
}
