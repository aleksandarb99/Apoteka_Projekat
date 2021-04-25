import React, {useState, useEffect} from "react";
import {Row, Container} from "react-bootstrap";

import FullCalendar from "@fullcalendar/react";
import dayGridPlugin from "@fullcalendar/daygrid";
import timeGridPlugin from "@fullcalendar/timegrid";
import resourceTimelinePlugin from "@fullcalendar/resource-timeline";
import AppointmentStartModal from "./appointment_start_modal";
import moment from "moment";
import api from "../../app/api";
import "../../styling/calendar.css";
import tippy from "tippy.js";
import 'tippy.js/dist/tippy.css';
import { getUserTypeFromToken } from '../../app/jwtTokenUtils';
import { getIdFromToken } from '../../app/jwtTokenUtils';

function WorkCalendar() {
    const [eventi, setEventi] = useState([]);
    const [showModal, setShowModal] = useState(false);
    const [startAppt, setStartAppt] = useState({});
    const [currUser, setCurrUser] = useState({});
    
    // const [eventi, setEventi] = useState([{ title: "today's event 2", start: moment().valueOf(), end: moment().valueOf() + 5000, display: 'auto', patient: 'djura djuric'},
    // { title: "today's event 2", start: moment().valueOf(), end: moment().valueOf() + 5000, display: 'auto', patient: 'djura djuric'},{ title: "today's event 2", start: moment().valueOf(), end: moment().valueOf() + 5000, display: 'auto', patient: 'djura djuric'},
    // { title: "today's event 2", start: moment().valueOf(), end: moment().valueOf() + 5000, display: 'auto', patient: 'djura djuric'},{ title: "today's event 2", start: moment().valueOf(), end: moment().valueOf() + 5000, display: 'auto', patient: 'djura djuric'}]);

    useEffect(() => {
        async function fetchAppointments() {
            let user_id = getIdFromToken();
            let user_type= getUserTypeFromToken().trim();
            console.log(user_type);
            if (!user_id){
                alert("invalid user!")
                setEventi([]);
                return;
            }else if (user_type !== 'DERMATOLOGIST' && user_type !== 'PHARMACIST'){
                alert("invalid user_type!")
                setEventi([]);
                return;
            }
            setCurrUser({id: user_id, type: user_type})
            const request = await api.get("http://localhost:8080/api/workers/calendarAppointments/" + user_id); //todo id je hardkodovan
            setEventi(request.data);
        
            return request;
        }
        fetchAppointments();
      }, []);


    const initiateAppt = (info) => {
        if (info.event.extendedProps.appointmentState !== 'RESERVED'){
            return;
        }

        if (!(moment(Date.now()) > moment(info.event.extendedProps.start).subtract(15, 'minutes'))){
            // nikako ga ne mozemo zapoceti vise od 15 minuta ranije
            alert("You can't initiate this appointment yet!");
            return;
        }
        let appt = {} //uga buga zbog id-a
        for(var k in info.event.extendedProps){ 
            appt[k]=info.event.extendedProps[k];
        }
        appt.id = info.event.id;
        setStartAppt(appt);
        setShowModal(true);
    }

    const onCancelMethod = () => { 
        setShowModal(false);
        let id = getIdFromToken();
        if (!id){
            alert("invalid user!")
            setEventi([]);
            return;
        }
        api.get("http://localhost:8080/api/workers/calendarAppointments/" + id).then((resp) => setEventi(resp.data)); 
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
                                    '<p>' + propi.patient + '<br/>' + (currUser.type==="DERMATOLOGIST" ? propi.pharmacy + '<br/><i>' : '<i>') + propi.appointmentState + '</i></p></div'
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
                                    '<p>' + propi.patient  + '<br/>' + (currUser.type==="DERMATOLOGIST" ? propi.pharmacy + '<br/><i>' : '<i>') +  propi.appointmentState + '</i></p></div'};
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
                                    +  "<br/>" + propi.patient + "<br/>"+ (currUser.type==="DERMATOLOGIST" ? propi.pharmacy + '<br/><i>' : '<i>')  + propi.appointmentState + "</i>"};
                            }
                        }
                    }}
            />
            <AppointmentStartModal show={showModal} onCancelMethod={onCancelMethod} appointment={startAppt} onHide={() => {setShowModal(false); setStartAppt({})}}></AppointmentStartModal>
        </div>
  );
}

export default WorkCalendar;