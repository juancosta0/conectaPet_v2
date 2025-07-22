import { Component, EventEmitter, Input, Output } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-primary-button',
  standalone: true,
  imports: [MatIconModule],
  templateUrl: './primary-button.component.html',
  styleUrl: './primary-button.component.scss'
})
export class PrimaryButtonComponent {
  @Input() action: 'enviar' | 'voltar' | 'limpar' = 'enviar';
  @Input() label: string = '';
  @Input() type: 'button' | 'submit' | 'reset' = 'button';
  @Input() disabled: boolean = false;
  @Output() clicked = new EventEmitter<void>();

  get icon(): string {
    switch (this.action) {
      case 'enviar': return 'send';
      case 'voltar': return 'arrow_back';
      case 'limpar': return 'clear';
      default: return '';
    }
  }

  onClick() {
    this.clicked.emit();
  }
}
