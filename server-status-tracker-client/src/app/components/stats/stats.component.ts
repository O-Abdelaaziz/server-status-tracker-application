import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-stats',
  standalone: true,
  imports: [],
  templateUrl: './stats.component.html',
  styleUrl: './stats.component.css',
})
export class StatsComponent {
  @Input()
  public servers: number = 0;
  @Input()
  public serversUp: number = 0;
  @Input()
  public serversDown: number = 0;
}
