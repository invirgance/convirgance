/*
 * Copyright 2024 INVIRGANCE LLC

Permission is hereby granted, free of charge, to any person obtaining a copy 
of this software and associated documentation files (the “Software”), to deal 
in the Software without restriction, including without limitation the rights to 
use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies 
of the Software, and to permit persons to whom the Software is furnished to do 
so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all 
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE 
SOFTWARE.
 */
package com.invirgance.convirgance.output;

import com.invirgance.convirgance.input.JSONInput;
import com.invirgance.convirgance.json.JSONArray;
import com.invirgance.convirgance.json.JSONObject;
import com.invirgance.convirgance.source.ByteArraySource;
import com.invirgance.convirgance.target.ByteArrayTarget;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author jbanes
 */
public class JSONOutputTest
{

    @Test
    public void testEmpty() throws Exception
    {
        ByteArrayTarget out = new ByteArrayTarget();
        JSONArray array = new JSONArray("[{},{},{}]");
        
        new JSONOutput().write(out, array);
        
        assertEquals("[\n{},\n{},\n{}\n]\n", new String(out.getBytes(), "UTF-8"));
    }
    
    @Test
    public void testObjects() throws Exception
    {
        ByteArrayTarget out = new ByteArrayTarget();
        JSONArray array = new JSONArray();
        JSONObject input = new JSONObject();
        
        int count = 0;
        
        input.put("string", "Hello world!");
        input.put("boolean", true);
        input.put("number", 1337);
        
        array.add(input);
        array.add(input);
        array.add(input);
        
        new JSONOutput().write(out, array);

        for(JSONObject record : new JSONInput().read(new ByteArraySource(out.getBytes())))
        {
            assertEquals(3, record.size());
            assertEquals("Hello world!", record.getString("string"));
            assertTrue(record.getBoolean("boolean"));
            assertEquals(1337, record.get("number"));
            
            count++;
        }
        
        assertEquals(3, count);
    }
}
