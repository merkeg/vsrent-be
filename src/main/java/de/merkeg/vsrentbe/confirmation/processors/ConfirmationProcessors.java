package de.merkeg.vsrentbe.confirmation.processors;

import de.merkeg.vsrentbe.confirmation.Process;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ConfirmationProcessors {
    Map<Process, List<ConfirmationProcessor>> processors;

    public ConfirmationProcessors(Collection<ConfirmationProcessor> processorCollection) {
        processors = processorCollection.stream().collect(Collectors.groupingBy(ConfirmationProcessor::processKey));

    }

    public ConfirmationProcessor getProcessor(Process process){
        return processors.get(process).get(0);
    }


}
