import React, {useState, useEffect} from "react";
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
import { useToasts } from "react-toast-notifications";
import "../../styling/worker.css";

function WorkCalendar() {
    const [eventi, setEventi] = useState([]);
    const [showModal, setShowModal] = useState(false);
    const [startAppt, setStartAppt] = useState({});
    const [currUser, setCurrUser] = useState({});

    const { addToast } = useToasts();

    useEffect(() => {
        async function fetchAppointments() {
            let user_id = getIdFromToken();
            let user_type= getUserTypeFromToken().trim();
            console.log(user_type);
            if (!user_id){
                addToast("No user id in token! error", { appearance: "error" });
                setEventi([]);
                return;
            }else if (user_type !== 'DERMATOLOGIST' && user_type !== 'PHARMACIST'){
                addToast("Invalid user type", { appearance: "error" });
                setEventi([]);
                return;
            }
            setCurrUser({id: user_id, type: user_type})
            await api.get("http://localhost:8080/api/workers/calendarAppointments/" + user_id)
                        .then((resp) => {
                            let appts = resp.data;
                            api.get("http://localhost:8080/api/vacation/getAcceptedVacationsFromWorker?id=" + user_id)
                                .then((resp) => {
                                    let reqVac = resp.data
                                    setEventi(appts.concat(reqVac));
                                }).catch(()=> { setEventi(appts); addToast("Error while getting vacations from server!", { appearance: "error" });});
                        }).catch(()=> {setEventi([]); addToast("Error while getting events!", { appearance: "error" });});
        }
        fetchAppointments();
      }, []);


    const initiateAppt = (info) => {
        if (info.event.extendedProps.calendarType !== 'appointment'){
            return;
        }
        if (info.event.extendedProps.appointmentState !== 'RESERVED'){
            return;
        }

        if (!(moment(Date.now()) > moment(info.event.extendedProps.start).subtract(15, 'minutes'))){
            // nikako ga ne mozemo zapoceti vise od 15 minuta ranije
            addToast("You can't initiate this appointment yet!", { appearance: "error" });
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
            addToast("Invalid user!", { appearance: "error" });
            setEventi([]);
            return;
        }
        //todo ovde probaj samo da promenis atribut bez opet gettovanja
        api.get("http://localhost:8080/api/workers/calendarAppointments/" + id)
            .then((resp) => {
                let appts = resp.data;
                api.get("http://localhost:8080/api/vacation/getAcceptedVacationsFromWorker?id=" + id)
                    .then((resp) => {
                        let reqVac = resp.data
                        setEventi(appts.concat(reqVac));
                    }).catch(()=> setEventi(appts));
            }).catch(()=> setEventi([]));
    }

    return (
        <div className="my__container p-5" style={{minHeight: "100vh"}}>
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
                                if (propi.calendarType === 'appointment'){
                                    return {html:
                                        '<div>' + propi.patient + '</div>'};
                                }else{
                                    return {html:
                                        '<div>' + propi.absenceType + '</div>'};
                                }
                            },
                            eventDidMount: function(info){
                                let propi = info.event.extendedProps;
                                if (propi.calendarType === 'appointment'){
                                    tippy(info.el, {
                                        allowHTML: true,
                                        content: '<div><p>'+  moment(info.event.start).format('HH:mm') + "-" + moment(info.event.end).format('HH:mm')   + '</p>'+
                                        '<p>' + propi.patient + '<br/>' + (currUser.type==="DERMATOLOGIST" ? propi.pharmacy + '<br/><i>' : '<i>') + propi.appointmentState + '</i></p></div>'
                                    });
                                }else{
                                    info.event.setProp('backgroundColor', 'rgba(20, 87, 97, 0.897)');
                                    tippy(info.el, {
                                        allowHTML: true,
                                        content: '<div><p>'+  moment(info.event.start).format('DD MMM') + "-" + moment(info.event.end).format('DD MMM')   + '</p>'+
                                                    '<p>' + propi.absenceType + '</p></div>'
                                    });
                                }
                            }
                        },
                        MonthView: {
                            type: 'dayGrid',
                            buttonText: 'Month View',
                            duration: {months: 1},
                            slotDuration: { days: 1 },
                            dayMaxEvents: 2,
                            eventContent: function(info){
                                let propi = info.event.extendedProps;
                                // return {html: moment(info.event.start).format('HH:mm') + "-" + moment(info.event.end).format('HH:mm') 
                                //     +  "<p>" + propi.patient + "</p><p><i> " + propi.appointmentState + "</i></p>"};
                                if (propi.calendarType === 'appointment'){
                                    return {html:
                                        '<div><p>'+  moment(info.event.start).format('HH:mm') + "-" + moment(info.event.end).format('HH:mm')   + '</p>'+
                                        '<p>' + propi.patient  + '<br/>' + (currUser.type==="DERMATOLOGIST" ? propi.pharmacy + '<br/><i>' : '<i>') +  propi.appointmentState + '</i></p></div>'};
                                }else{
                                    return {html:
                                        '<div><p>'+  moment(info.event.start).format('DD MMM') + "-" + moment(info.event.end).format('DD MMM')   + '</p>'+
                                                    '<p>' + propi.absenceType + '</p></div>'};
                                }
                            },
                            eventDidMount: function(info){
                                if (info.event.extendedProps.calendarType !== 'appointment'){
                                    info.event.setProp('backgroundColor', 'rgba(20, 87, 97, 0.897)');
                                }
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
                                if (propi.calendarType === 'appointment'){
                                    return {html: moment(info.event.start).format('DD/MM | HH:mm') + "-" + moment(info.event.end).format('HH:mm') 
                                        +  "<br/>" + propi.patient + "<br/>"+ (currUser.type==="DERMATOLOGIST" ? propi.pharmacy + '<br/><i>' : '<i>')  + propi.appointmentState + "</i>"};
                                }else{
                                    return {html:
                                        '<div><p>'+  moment(info.event.start).format('DD MMM') + "-" + moment(info.event.end).format('DD MMM')   + '</p>'+
                                                    '<p>' + propi.absenceType + '</p></div>'};
                                }
                            },
                            eventDidMount: function(info){
                                if (info.event.extendedProps.calendarType !== 'appointment'){
                                    info.event.setProp('backgroundColor', 'rgba(20, 87, 97, 0.897)');
                                }
                            }
                        }
                    }}
            />
            <AppointmentStartModal show={showModal} onCancelMethod={onCancelMethod} appointment={startAppt} onHide={() => {setShowModal(false); setStartAppt({})}}></AppointmentStartModal>
        </div>
  );
}

export default WorkCalendar;