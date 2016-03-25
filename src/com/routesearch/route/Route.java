/**
 * 实现代码文件
 * 
 * @author XXX
 * @since 2016-3-4
 * @version V1.0
 */
package com.routesearch.route;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;  
import java.util.PriorityQueue; 
import java.util.Queue;

public final class Route
{
    /**
     * 你需要完成功能的入口
     * 
     * @author XXX
     * @since 2016-3-4
     * @version V1
     */
	
    public static String searchRoute(String graphContent, String condition)
    {
        String [] lines = graphContent.split("\n");
        String [] conditions = condition.split(",");
        String startPoint = conditions[0];
        String endPoint = conditions[1];
        String passPoints =conditions[2].replace("\n","");
        if(passPoints ==""){
        	passPoints = "|";
        }
        else{
        	passPoints = "|" + passPoints + "|";
        }
        Map<String,ArrayList<String[]>> startPointMap = new HashMap();
        Queue<String[]> frontier = new PriorityQueue<String[]>(new Comparator<String[]>(){
        	public int compare(String[] s1, String[] s2){
        		return Integer.parseInt(s1[2]) - Integer.parseInt(s2[2]);
        	}
        });
        ArrayList<String> exploredPoints = new ArrayList<String>();
       
        
        //arrange the lines as structure startPoint:[path,path,path]
        int i;
        for (i=0; i<lines.length; i++){
        	String[] tempLines = lines[i].split(",");
        	String[] pathDetail = new String[3];
        	pathDetail[0] = tempLines[0];
        	pathDetail[1] = tempLines[2];
        	pathDetail[2] = tempLines[3];
        	if(startPointMap.containsKey(tempLines[1])){
        		startPointMap.get(tempLines[1]).add(pathDetail);
        	}
        	else{
        		ArrayList<String[]> pathes = new ArrayList<String[]>();
        		pathes.add(pathDetail);
        		startPointMap.put(tempLines[1],pathes);
        	}
        }
        
        //UCS
        String[] initialNode = new String[4];
        initialNode[0] = startPoint;
        initialNode[1] = "";  //passed pathes
        initialNode[2] = "0"; //cost
        initialNode[3] = passPoints.replace("|"+startPoint+"|", "|"); //rest pass points
        frontier.add(initialNode);
        while(!frontier.isEmpty()){
        	String[] currentNode = frontier.poll();
        	if(exploredPoints.contains(currentNode[0])){
        		continue;
        	}
        	//check whether goal is reached
        	if(currentNode[0]==endPoint && currentNode[3]=="|"){
        		return currentNode[1];
        	}
        	else{
        		//explore current node
        		if(!startPointMap.containsKey(currentNode[0])){
        			continue;
        		}
        		else{
        			//expand the current node
        			ArrayList<String[]>ps = startPointMap.get(currentNode[0]);
        			for (String[] p : ps){
        				String [] node = new String[4];
        				node[0] = p[1];
        				node[1] = currentNode[1]+p[0]+"|";
        				node[2] = Integer.toString((Integer.parseInt(currentNode[2]) + Integer.parseInt(p[2])));
        				//cut the point if it is in the condition
        				node[3] = currentNode[3].replace("|"+p[1]+"|", "|");
        				frontier.add(node);
        			}
        		}
        		exploredPoints.add(currentNode[0]);
        		
        	}
        }
        return "NA";
        //A* cost + heuristic
        
 
    }
  
  
}