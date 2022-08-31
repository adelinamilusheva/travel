import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-list-statistics',
  templateUrl: './list-statistics.component.html',
  styleUrls: ['./list-statistics.component.css']
})
export class ListStatisticsComponent implements OnInit {

  constructor( 
    public authService: AuthService
    ) { }

  ngOnInit(): void {
  }

}
