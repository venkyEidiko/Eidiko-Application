import { Component } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {
  tab1Content = `<p>Content for Tab 1</p>`;
  tab2Content = `<div><h2>Content for Tab 2</h2><p>This is a paragraph.</p></div>`;
  
  homeTabs=[
    {
      label:'Dashboard',content:this.tab1Content
    },{
      label:'Welcome',content:this.tab2Content
    }
  ];
 

}
