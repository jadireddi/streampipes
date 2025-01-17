/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

import { FileManagementUtils } from './FileManagementUtils';
import { ConnectUtils } from './connect/ConnectUtils';
import { GenericAdapterBuilder } from '../builder/GenericAdapterBuilder';

export class PrepareTestDataUtils {
    public static dataName = 'prepared_data';

    public static loadDataIntoDataLake(
        dataSet: string,
        format: 'csv' | 'json_array' = 'csv',
        storeInDataLake: boolean = true,
    ) {
        // Create adapter with dataset
        FileManagementUtils.addFile(dataSet);

        const adapter = this.getDataLakeTestSetAdapter(
            PrepareTestDataUtils.dataName,
            format,
            storeInDataLake,
        );
        ConnectUtils.addGenericSetAdapter(adapter);

        // Wait till data is stored
        if (storeInDataLake) {
            cy.wait(10000);
        }
    }

    private static getDataLakeTestSetAdapter(
        name: string,
        format: 'csv' | 'json_array',
        storeInDataLake: boolean = true,
    ) {
        const adapterBuilder = GenericAdapterBuilder.create('File_Set')
            .setName(name)
            .setTimestampProperty('timestamp');

        if (format === 'csv') {
            adapterBuilder
                .setFormat('csv')
                .addFormatInput('input', 'delimiter', ';')
                .addFormatInput('checkbox', 'header', 'check');
        } else {
            adapterBuilder.setFormat('json_array');
        }

        if (storeInDataLake) {
            adapterBuilder.setStoreInDataLake();
        }
        return adapterBuilder.build();
    }
}
