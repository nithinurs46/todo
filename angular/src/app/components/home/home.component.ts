import { Component, OnInit } from '@angular/core';

@Component({
    selector: 'app-home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
    pieData: any;
    barData: any;

    constructor() { }

    ngOnInit() {
        this.displayPieChart();
        this.displayBarChart();
    }

    displayPieChart() {
        this.pieData = {
            labels: ['Completed', 'Pending'],
            datasets: [
                {
                    data: [300, 50],
                    backgroundColor: [
                        "#FF6384",
                        "#36A2EB",
                    ],
                    hoverBackgroundColor: [
                        "#FF6384",
                        "#36A2EB",
                    ]
                }]
        };
    }

    displayBarChart() {
        this.barData = {
            labels: ['Completed', 'Pending'],
            datasets: [
                {
                    label: 'Today',
                    backgroundColor: '#42A5F5',
                    borderColor: '#1E88E5',
                    data: [65, 59, 80, 81, 56, 55]
                },
                {
                    label: 'Past',
                    backgroundColor: '#ffc107',
                    borderColor: '#7CB342',
                    data: [28, 48, 40, 19, 86, 27]
                }
            ]
        }
    }

}
