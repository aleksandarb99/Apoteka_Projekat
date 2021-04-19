import React, {useState, useEffect} from "react";
import {Row, Container} from "react-bootstrap";

import FullCalendar from "@fullcalendar/react";
import dayGridPlugin from "@fullcalendar/daygrid";
import timeGridPlugin from "@fullcalendar/timegrid";
import resourceTimelinePlugin from "@fullcalendar/resource-timeline";
import AppointmentStartModal from "./appointment_start_modal";
import moment from "moment";
import axios from "axios";
import "./calendar.css";
import tippy from "tippy.js";
import 'tippy.js/dist/tippy.css';

function WorkCalendar() {
    const [eventi, setEventi] = useState([]);
    const [showModal, setShowModal] = useState(false);
    const [startAppt, setStartAppt] = useState({});
    // const [eventi, setEventi] = useState([{ title: "today's event 2", start: moment().valueOf(), end: moment().valueOf() + 5000, display: 'auto', patient: 'djura djuric'},
    // { title: "today's event 2", start: moment().valueOf(), end: moment().valueOf() + 5000, display: 'auto', patient: 'djura djuric'},{ title: "today's event 2", start: moment().valueOf(), end: moment().valueOf() + 5000, display: 'auto', patient: 'djura djuric'},
    // { title: "today's event 2", start: moment().valueOf(), end: moment().valueOf() + 5000, display: 'auto', patient: 'djura djuric'},{ title: "today's event 2", start: moment().valueOf(), end: moment().valueOf() + 5000, display: 'auto', patient: 'djura djuric'}]);

    useEffect(() => {
        async function fetchAppointments() {
            const request = await axios.get("http://localhost:8080/api/workers/calendarAppointments/5"); //todo id je hardkodovan
            setEventi(request.data);
        
            return request;
        }
        fetchAppointments();
      }, []);

    const initiateAppt = (info) => {
        if (info.event.extendedProps.appointmentState != 'RESERVED'){
            return;
        }
        
        // if (!(moment(Date.now()).subtract(15, 'minutes') < info.event.start && moment(Date.now()).add(3, 'hours') > info.event.start)){
        //     // 3 sata da bi mogao da se cancelluje i kasnije, ali svakako nece moci da se zapocne ranije
        //     alert("You can't initiate this appointment yet!");
        //     return;
        // }
        console.log(info.event);
        setStartAppt(info.event.extendedProps);
        setShowModal(true);
    }

    return (
        <div>
            <FullCalendar
                    plugins={[dayGridPlugin, timeGridPlugin, resourceTimelinePlugin]}
                    headerToolbar={{
                        left: 'prev,next today',
                        center: 'title',
                        right: 'WeekView,MonthView,YearView'
                    }}
                    allDaySlot={false}
                    events={eventi}
                    initialView='MonthView'
                    eventClick={initiateAppt}
                    views= {{
                        WeekView: {
                            type: 'timeGrid',
                            buttonText: 'Week View',
                            duration: {weeks: 1},
                            eventContent: function(info){
                                let propi = info.event.extendedProps;
                                // return {html: moment(info.event.start).format('HH:mm') + "-" + moment(info.event.end).format('HH:mm') 
                                //     +  "<p>" + propi.patient + "</p><p><i> " + propi.appointmentState + "</i></p>"};
                                return {html:
                                    '<div>' + propi.patient + '</div>'};
                            },
                            eventDidMount: function(info){
                                let propi = info.event.extendedProps;
                                tippy(info.el, {
                                    allowHTML: true,
                                    content: '<div><p>'+  moment(info.event.start).format('HH:mm') + "-" + moment(info.event.end).format('HH:mm')   + '</p>'+
                                    '<p>' + propi.patient + '<br/><i>' +  propi.appointmentState + '</i></p></div'
                                  });
                            }
                        },
                        MonthView: {
                            type: 'dayGrid',
                            buttonText: 'Month View',
                            duration: {months: 1},
                            slotDuration: { days: 1 },
                            dayMaxEvents: 4,
                            eventContent: function(info){
                                let propi = info.event.extendedProps;
                                // return {html: moment(info.event.start).format('HH:mm') + "-" + moment(info.event.end).format('HH:mm') 
                                //     +  "<p>" + propi.patient + "</p><p><i> " + propi.appointmentState + "</i></p>"};
                                return {html:
                                    '<div><p>'+  moment(info.event.start).format('HH:mm') + "-" + moment(info.event.end).format('HH:mm')   + '</p>'+
                                    '<p>' + propi.patient + '<br/><i>' +  propi.appointmentState + '</i></p></div'};
                            }
                        },
                        YearView: {
                            type: 'timeline',
                            buttonText: 'Year view',
                            duration: { years: 1 },
                            slotDuration: { months: 1 },
                            visibleRange: {
                                    start: moment().startOf('year').toDate(),
                                    end: moment().endOf("year").toDate()
                            },
                            eventContent: function(info){
                                let propi = info.event.extendedProps;
                                return {html: moment(info.event.start).format('DD/MM | HH:mm') + "-" + moment(info.event.end).format('HH:mm') 
                                    +  "<br/>" + propi.patient + "<br/><i>" + propi.appointmentState + "</i>"};
                            }
                        }
                    }}
            />
            <AppointmentStartModal show={showModal} appointment={startAppt} onHide={() => {setShowModal(false); setStartAppt({})}}></AppointmentStartModal>
        </div>
  );
}

export default WorkCalendar;