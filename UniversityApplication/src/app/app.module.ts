;
import { BrowserAnimationsModule } from '@angular/platform-browser/animations'
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { ErrorComponent } from './error/error.component';
//import { AppRoutingRoutingModule } from './app-routing-routing.module';
import { RouterModule, Routes } from '@angular/router';
import { TableComponent } from './table/table.component';
import { MatPaginatorModule} from '@angular/material/paginator';
import { MatTableModule} from '@angular/material/table';
const routes: Routes = [
  //{path:'table',component:TableComponent},
  {path:'**',component:ErrorComponent}
];

@NgModule({
  declarations: [
    AppComponent,
    ErrorComponent,
    TableComponent
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot(routes),
    BrowserAnimationsModule,
    MatPaginatorModule,MatTableModule
    
  
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
