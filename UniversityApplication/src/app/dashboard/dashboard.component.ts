import { Component } from '@angular/core';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent {
  selectedTab: string = 'organization';
  selectedContent: string = 'announcement';
  currentDate = new Date();

  selectTab(tab: string) {
    this.selectedTab = tab;
  }

  showContent(event: Event, content: string) {
    event.preventDefault();
    this.selectedContent = content;
  }
}
