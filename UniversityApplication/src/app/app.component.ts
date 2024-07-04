import { Component } from '@angular/core';
import { LoaderService } from './services/loader.service';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'UniversityApplication';
 // constructor(private loaderService: LoaderService) {}

  // isLoading: any = this.loaderService.isLoading;
  
}
