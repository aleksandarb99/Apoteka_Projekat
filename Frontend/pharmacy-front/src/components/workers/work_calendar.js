import React, {useState, useEffect} from "react";
import {Row, Container} from "react-bootstrap";

import FullCalendar from "@fullcalendar/react";
import dayGridPlugin from "@fullcalendar/daygrid";
import timeGridPlugin from "@fullcalendar/timegrid";
import resourceTimelinePlugin from "@fullcalendar/resource-timeline";
import { Calendar } from "@fullcalendar/core";
import moment from "moment";


function WorkCalendar() {
    const [eventi, setEventi] = useState([{ title: "today's event 2", start: moment().valueOf(), end: moment().valueOf() + 5000, display: 'auto'},
    { title: "today's event 2", start: moment().valueOf(), end: moment().valueOf() + 5000, display: 'auto'},{ title: "today's event 2", start: moment().valueOf(), end: moment().valueOf() + 5000, display: 'auto'},
    { title: "today's event 2", start: moment().valueOf(), end: moment().valueOf() + 5000, display: 'auto'},{ title: "today's event 2", start: moment().valueOf(), end: moment().valueOf() + 5000, display: 'auto'}]);
  return (
    <div>
        <Container>
            <FullCalendar
                plugins={[dayGridPlugin, timeGridPlugin, resourceTimelinePlugin]}
                headerToolbar={{
                  left: 'prev,next today',
                  center: 'title',
                  right: 'dayGridMonth,timeGridWeek,CustomView,YearView'
                }}
                allDaySlot={false}
                events={eventi}
                views= {{
                    CustomView: {
                        type: 'dayGrid',
                        buttonText: 'my Custom View',
                        duration: {months: 1},
                        dayMaxEvents: 3
                    },
                    YearView: {
                        type: 'timeline',
                        buttonText: 'Year view',
                        dateIncrement: { years: 1 },
                        slotDuration: { months: 1 },
                        visibleRange: {
                                start: moment().startOf('year').toDate(),
                                end: moment().endOf("year").toDate()
                        }
                    }
                }}
            />
        </Container>
    </div>
  );
}

export default WorkCalendar;