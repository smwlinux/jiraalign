package jiraalign
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.history.ChangeItemBean;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.changehistory.ChangeHistory
import com.atlassian.jira.issue.changehistory.ChangeHistoryManager
//import com.atlassian.jira.user.util.UserManager 
import java.util.Date.*

//ApplicationUser
class AlignedAgility {
   public static String getDateOfLastStatusEntry(String statusName,Issue issue){
		def changeHistoryManager = ComponentAccessor.getChangeHistoryManager()
        def foundstatus=false
        def statuschange
        def changeItems = changeHistoryManager.getChangeItemsForField(issue, "status")
        changeItems.reverse().each {ChangeItemBean item -> item.fromString
         if (item.toString == statusName && foundstatus==false ){
             foundstatus=true
             statuschange= item.created.getDateTimeString()
         }
	}

		return statuschange.toString()
    }
    public static double getTotalDurationInStatusHours(String statusName,Issue issue){
        long timeDiff
        long totalTime
        Date firstStatusChange=null
        List<Long> rt = new LinkedList<>()
        def currentTime=System.currentTimeMillis()
        def myCurrentStatus=issue.status.getName()
        ChangeHistoryManager changeHistoryManager = ComponentAccessor.getChangeHistoryManager()
        def changeItems = changeHistoryManager.getChangeItemsForField(issue, "status")
        long previousDate
        rt.clear()
        rt.add(0L)
        changeItems.reverse().each {ChangeItemBean item -> item.fromString
            if(item.getToString()==statusName){
                if(!previousDate && myCurrentStatus==statusName){
                   previousDate=currentTime
                }
                timeDiff=previousDate-item.created.time
                rt.add(timeDiff)
                }
                previousDate=item.created.time
                firstStatusChange=item.created
        }
	    if(statusName=="Pending Approval"){
            if(previousDate!=0 && previousDate!=null ){
                timeDiff=previousDate-issue.created.time
            }else{
                timeDiff=currentTime-issue.created.time
            }    
        rt.add(timeDiff)
        }
        totalTime=(long) rt.sum()
	    double TotalDuration=((totalTime/1000)/3600)
	    TotalDuration=Math.round(TotalDuration*100)/100
	    return TotalDuration
    }
    public static getUserOfLastStatusEntry(String statusName,Issue issue){


    }

}







