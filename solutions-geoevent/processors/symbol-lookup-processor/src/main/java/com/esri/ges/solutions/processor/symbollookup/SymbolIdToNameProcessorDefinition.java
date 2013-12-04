/*
 | Copyright 2013 Esri
 |
 | Licensed under the Apache License, Version 2.0 (the "License");
 | you may not use this file except in compliance with the License.
 | You may obtain a copy of the License at
 |
 |    http://www.apache.org/licenses/LICENSE-2.0
 |
 | Unless required by applicable law or agreed to in writing, software
 | distributed under the License is distributed on an "AS IS" BASIS,
 | WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 | See the License for the specific language governing permissions and
 | limitations under the License.
 */
package com.esri.ges.solutions.processor.symbollookup;

import java.util.Collection;
import java.util.Iterator;

import com.esri.ges.core.geoevent.GeoEventDefinition;
import com.esri.ges.core.property.PropertyDefinition;
import com.esri.ges.core.property.PropertyException;
import com.esri.ges.core.property.PropertyType;
import com.esri.ges.manager.geoeventdefinition.GeoEventDefinitionManager;
import com.esri.ges.processor.GeoEventProcessorDefinitionBase;

public class SymbolIdToNameProcessorDefinition extends GeoEventProcessorDefinitionBase
{
	private GeoEventDefinitionManager manager = null;	
	
	public SymbolIdToNameProcessorDefinition()
	{
		;
	}

	public void setManager(GeoEventDefinitionManager m) throws PropertyException {
		manager = m;
		
		PropertyDefinition procSymbolIdSource = new PropertyDefinition("symbolIdSource", 
				PropertyType.String, "", "SymbolId Source", "Source of SymbolId Value", true, false);
		procSymbolIdSource.addAllowedValue("Event");
		propertyDefinitions.put(procSymbolIdSource.getPropertyName(), procSymbolIdSource);
		
		PropertyDefinition procSymbolIdEvent = new PropertyDefinition("symbolIdEvent", 
				PropertyType.String, "", "SymbolId Event Field", "Geoevent field containing SymbolId data", true, false);
		procSymbolIdEvent.setDependsOn("symbolIdSource=Event");
		setGeoEventAllowedFields(procSymbolIdEvent);
		propertyDefinitions.put(procSymbolIdEvent.getPropertyName(), procSymbolIdEvent);
		
		PropertyDefinition procSymbolNameSource = new PropertyDefinition("symbolNameSource", 
				PropertyType.String, "", "SymbolName Source", "Source of SymbolName Value", true, false);
		procSymbolNameSource.addAllowedValue("Event");
		propertyDefinitions.put(procSymbolNameSource.getPropertyName(), procSymbolNameSource);
		
		PropertyDefinition procSymbolNameEvent = new PropertyDefinition("symbolNameEvent", 
				PropertyType.String, "", "SymbolName Event Field", "Geoevent field containing SymbolName data", true, false);
		procSymbolNameEvent.setDependsOn("symbolNameSource=Event");
		setGeoEventAllowedFields(procSymbolNameEvent);
		propertyDefinitions.put(procSymbolNameEvent.getPropertyName(), procSymbolNameEvent);
		
	}
	
	private void setGeoEventAllowedFields(PropertyDefinition pd)
	{
		if (manager == null)
			return;
		
		Collection<GeoEventDefinition> geodefs = this.manager.listAllGeoEventDefinitions();
		Iterator<GeoEventDefinition> it = geodefs.iterator();
		GeoEventDefinition geoEventDef;
		while (it.hasNext())
		{
			geoEventDef = it.next();
			String defName = geoEventDef.getName();
			for(int i = 0; i < geoEventDef.getFieldDefinitions().size(); ++i)
			{
				String fld = geoEventDef.getFieldDefinitions().get(i).getName();
				pd.addAllowedValue(defName + ":" + fld);
			}
		}
	}
	
	@Override
	public String getName()
	{
		return "SymbolIdToNameProcessor";
	}

	@Override
	public String getDomain()
	{
		return "com.esri.ges.processor";
	}

	@Override
	public String getVersion()
	{
		return "10.2.0";
	}

	@Override
	public String getLabel()
	{
		return "Symbol Id To Name Processor";
	}

	@Override
	public String getDescription()
	{
		return "Converts Symbol ID Codes (SIDCs) to well known symbol names.";
	}

	@Override
	public String getContactInfo()
	{
		return "geoeventprocessor@esri.com";
	}
}