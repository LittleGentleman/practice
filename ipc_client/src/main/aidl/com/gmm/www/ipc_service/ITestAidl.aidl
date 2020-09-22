// ITestAidl.aidl
package com.gmm.www.ipc_service;

// Declare any non-default types here with import statements

import com.gmm.www.ipc_service.Person;

interface ITestAidl {
    void addPerson(in Person person);
    List<Person> getPersonList();
}
